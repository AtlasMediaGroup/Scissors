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
                    sh './gradlew createReobfPaperclipJar --no-daemon --refresh-dependencies'
                }
                sh """
                    #!/bin/sh
                    BRANCH=\$(echo "\${BRANCH_NAME}" | sed 's/\\//_/g')
                    mv \${WORKSPACE}/build/libs/Scissors-paperclip-*.jar \${WORKSPACE}/build/libs/scissors-\${BRANCH}-\${BUILD_NUMBER}.jar
                    rm \${WORKSPACE}/build/libs/Scissors-bundler-*.jar
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
                branch "1.20.1"
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
            archiveArtifacts artifacts: 'build/libs/scissors-*.jar', fingerprint: true
            junit 'Scissors-Server/build/test-results/test/*.xml'
            junit 'Scissors-API/build/test-results/test/*.xml'
            cleanWs()
        }
    }
}