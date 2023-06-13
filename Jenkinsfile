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
            archiveArtifacts artifacts: 'build/libs/Scissors-paperclip-*.jar', fingerprint: true
            junit 'Scissors-Server/build/test-results/test/*.xml'
            junit 'Scissors-API/build/test-results/test/*.xml'
            cleanWs()
        }
    }
}
