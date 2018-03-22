String getVersion() {
    def lastTag = sh(returnStdout: true, script: 'git describe --abbrev=0 --tags')
    def commitsSinceTag = sh(returnStdout: true, script: "git rev-list ${lastTag.trim()}.. --count")
    def commitId = sh(returnStdout: true, script: "git rev-parse --short HEAD")
    def buildTimestamp = new Date().format("yyyyMMddHHmmss")

    return "${lastTag.trim()}.${commitsSinceTag.trim()}-${buildTimestamp}-${commitId.trim()}"
}

try {
    timeout(time: 20, unit: 'MINUTES') {
        node('maven') {

            def releaseVersion = "1.0.${env.BUILD_NUMBER}"
            def applicationName = "eap-sampleapp"

            stage('Build') {
                dir('scm') {
                    checkout scm
                    releaseVersion = getVersion()

                    sh("./mvnw -B org.codehaus.mojo:versions-maven-plugin:2.2:set -U -DnewVersion=${releaseVersion}")
                    sh('./mvnw -B package fabric8:build -Popenshift -Dfabric8.build.strategy=docker')
                }
            }
        }
    }
} catch (err) {
    echo "in catch block"
    echo "Caught: ${err}"
    currentBuild.result = 'FAILURE'
    throw err
}
