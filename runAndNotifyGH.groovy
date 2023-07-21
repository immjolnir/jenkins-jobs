import hudson.AbortException
import org.jenkinsci.plugins.workflow.steps.FlowInterruptedException

// See: https://issues.jenkins-ci.org/browse/JENKINS-34376 for why this
// is as complex as it is...
def get_descriptions() {
    def abort_messages = [
        "The commits build was aborted",
    ]
    def error_messages = [
        "The commits build had some unexpected error",
        "The commits build did not work out, sorry try again",
    ]
    def started_messages = [
        "This commit is being built",
    ]
    def passed_messages = [
        "The commits build passed",
        "The commits build worked, amazing job",
        "This commit looks good",
        "Yo dawg, you did an amazing commit",
    ]
    def rand = new Random()
    def descriptions = [:]
    int n = rand.nextInt(abort_messages.size())
    descriptions["aborted"] = abort_messages[n]
    n = rand.nextInt(passed_messages.size())
    descriptions["passed"] = passed_messages[n]
    n = rand.nextInt(started_messages.size())
    descriptions["started"] = started_messages[n]
    n = rand.nextInt(passed_messages.size())
    descriptions["passed"] = passed_messages[n]
    return descriptions
}

def call(String pr_git_sha, String repo, String context, Closure c) {
    def descriptions = get_descriptions()

    def notifyStatus = { String status, String description ->
        github.notifyStatus(
            repo: repo, sha: pr_git_sha, context: context,
            status: status, description: description
        )
    }

    notifyStatus("PENDING", descriptions.started)

    def res = null
    try {
        res = c.call()
    }
    catch(AbortException e) {
        notifyStatus("FAILURE", descriptions.aborted)
        throw e
    }
    catch(FlowInterruptedException e) {
        notifyStatus("FAILURE", descriptions.aborted)
        throw e
    }
    catch(Exception e) {
        notifyStatus("FAILURE", descriptions.errored)
        throw e
    }

    notifyStatus("SUCCESS", descriptions.passed)

    return res
}
