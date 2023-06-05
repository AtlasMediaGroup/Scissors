pipeline {
    agent any
    environment {
        GITHUB_BRANCH = "${BRANCH_NAME}"
    }
    stages {
        stage('applyPatches') {
            steps {
                withGradle {
                    sh './gradlew applyPatches --no-daemon --refresh-dependencies'
                }
            }
        }
        stage('paperclipJar') {
            steps {
                withGradle {
                    sh './gradlew paperclipJar --no-daemon --refresh-dependencies'
                }
                sh """
                    #!/bin/sh
                    mv \${WORKSPACE}/build/libs/Scissors-*.jar \${WORKSPACE}/build/libs/scissors-\${BUILD_NUMBER}.jar
                    """
            }
        }
        stage('test') {
            steps {
                withGradle {
                    sh './gradlew test --no-daemon'
                }
            }
        }
        stage('publish') {
            when {
                branch "1.17.1"
            }
            steps {
                script {
                    try {
                        withCredentials([usernamePassword(credentialsId: 'scissors-ci', passwordVariable: 'scissorsPassword', usernameVariable: 'scissorsUser')]) {
                            withGradle {
                                sh "./gradlew :Scissors-API:publish --no-daemon"
                            }
                        }
                        true
                    } catch (_) {
                        false
                    }
                }
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            junit 'Scissors-Server/build/test-results/test/*.xml'
            junit 'Scissors-API/build/test-results/test/*.xml'
            cleanWs()
        }
    }
}
