// https://www.jenkins.io/doc/book/pipeline/syntax/#parallel

def Greet(name) {
	echo "Hello ${name}."
}

def mul(a, b)
{
   def mul=a*b
   return mul
}

pipeline {
    agent {
      label 'master'
    }
    parameters {
        string(name: 'x1', defaultValue: 'default', description: 'This is a parameter')
    }
    options {
        // https://stackoverflow.com/questions/47039924/jenkins-pipeline-enable-timestamps-in-build-log-console
        // https://linuxhint.com/timestamps-jenkins/
        timestamps()
        parallelsAlwaysFailFast()
    }
    stages {
        stage('Show pullRequest info') {
            steps {
                script {
                    // hudson.remoting.ProxyException: groovy.lang.MissingMethodException: No signature of method: org.jenkinsci.plugins.pipeline.github.PullRequestGroovyObject.listFiles() is applicable for argument types: () values: []

                    // See https://plugins.jenkins.io/github-api/
                    // https://github.com/jenkinsci/pipeline-github-plugin/blob/master/src/main/java/org/jenkinsci/plugins/pipeline/github/PullRequestGroovyObject.java
                    // changedfiles: [Jenkinsfile, file1, file2]
                    changedfiles = pullRequest.getFiles().collect { it.getFilename() }
                    changedfiles_num = pullRequest.getChangedFiles()
                    commits_num = pullRequest.getCommitCount()
                    draft = pullRequest.isDraft()

                    echo "changedfiles_num: ${changedfiles_num}"
                    echo "changedfiles: ${changedfiles}"
                    echo "commits_num: ${commits_num}"
                    echo "draft: ${draft}"

                    // perception_changed = pullRequest.getFiles().any { it.getFilename().startsWith("perception")? true: false }
                }
            }
        }
        stage('Non-Parallel Stage') {
            steps {
                echo 'This stage will be executed first.'
                script { // Call API of shared libraries
                    basename = getDirName("/var/jenkins_home/init.groovy.d/executors.groovy")
                    echo "BASE DIR: ${basename}"
                    log.info "hello ${basename}"
                }
            }
        }
        stage('Create Jenkins Jobs') {
            when {
                branch 'main'
            }
            steps {
                echo "Reconfigure jenkins jobs"
                sh 'jenkins-jobs --log_level DEBUG --ignore-cache --conf jenkins_jobs.ini update projects/'
            }
        }
        stage('Setup Slaves') {
            when {
                branch 'main'
            }
            steps {
                echo "Reconfigure jenkins slaves"
            }
        }
    }
    post {
      always {
        // One or more steps need to be included within each condition's block.
        echo "always"
      }
      aborted {
        // One or more steps need to be included within each condition's block.
        echo "aborted"
      }
      success {
        // One or more steps need to be included within each condition's block.
        echo "success"
      }
      failure {
        // One or more steps need to be included within each condition's block.
        echo "failure"
      }
      fixed {
        // One or more steps need to be included within each condition's block.
        echo "fixed"
      }
    }
}
