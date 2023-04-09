

stage("Hello Stage") {
    echo "Hello Jenkins!"

    script {
        basename = getDirName("/var/jenkins_home/init.groovy.d/executors.groovy")
        echo "BASE DIR: ${basename}"
    }
}
