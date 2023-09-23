pipeline {
    agent any
    environment {
        GITHUB_BRANCH = "${BRANCH_NAME}"
    }
    stages {
        stage('clean') {
            steps {
                withGradle {
                    sh './gradlew cleanCache'
                }
            }
        }
        stage('applyPatches') {
            steps {
                withGradle {
                    sh './gradlew applyPatches'
                }
            }
        }
        stage('paperclipJar') {
            steps {
                withGradle {
                    sh './gradlew createReobfPaperclipJar --stacktrace --info'
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