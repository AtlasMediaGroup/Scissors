pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh './gradlew applyPatches --no-daemon'
                sh './gradlew paperclipJar --no-daemon'
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            cleanWs()
        }
    }
}