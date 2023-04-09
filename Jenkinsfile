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
