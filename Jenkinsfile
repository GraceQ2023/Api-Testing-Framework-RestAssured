pipeline {
  agent any


  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Run API Tests in Docker') {
      steps {
        sh 'docker compose up --exit-code-from api-tests'
      }
    }

    stage('Publish Reports') {
      steps {
        junit 'target/surefire-reports/*.xml'
        archiveArtifacts artifacts: 'target/**', fingerprint: true
      }
    }
  }

  post {
    always {
      sh 'docker compose down'
    }
  }
}

