pipeline {
    agent any
    environment {
        GITHUB_BRANCH = "${BRANCH_NAME}"
    }
    options {
        buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')
    }
    stages {
        stage('cleanCache') {
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
                    sh './gradlew createReobfPaperclipJar'
                }
            }
        }
        stage('test') {
            steps {
                withGradle {
                    sh './gradlew test'
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