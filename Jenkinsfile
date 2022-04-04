pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh './gradlew applyPatches'
                sh './gradlew paperclipJar'
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
        }
    }
}