pipeline {
    agent any

    environment {
        DOCKER_CLI = "/usr/local/bin/docker"
        DOCKER_COMPOSE_FILE = "docker/docker-compose.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Skipping SCM checkout because Jenkinsfile is in UI"
                checkout scm
            }
        }

        stage('Debug Workspace') {
            steps {
                echo "Listing workspace files..."
                sh 'ls -R'
            }
        }

        stage('Run API Tests in Docker') {
            steps {
                echo "Starting API tests container..."
                sh "${DOCKER_CLI} compose -f ${DOCKER_COMPOSE_FILE} up --exit-code-from api-tests"
            }
        }

        stage('Publish Reports') {
            steps {
                // Surefire XML reports
                junit 'target/surefire-reports/*.xml'

                // Archive everything in target/ (includes ExtentReports)
                archiveArtifacts artifacts: 'target/**', fingerprint: true

                // Publish ExtentReport HTML
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target',             // folder containing extent-report.html
                    reportFiles: 'extent-report.html', // report file name
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            echo "Cleaning up Docker containers..."
            sh "${DOCKER_CLI} compose -f ${DOCKER_COMPOSE_FILE} down || true"
        }

        success {
            echo "Build SUCCESS"
        }

        failure {
            echo "Build FAILED"
        }
    }
}
