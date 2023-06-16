# Real-time Monitoring of Testing with Grafana and Jira Integration

This repository contains the source code and necessary resources to implement the real-time test monitoring solution with Grafana and Jira integration, as described in the postgraduate work "Real-time Test Monitoring with Grafana and Jira Integration to Improve Efficiency in Error Detection and Correction" by Juan Manuel Cuerva Gutiérrez.

## Project Description

This project aims to develop a solution for real-time monitoring of automated tests using open-source tools such as Grafana and Selenium. It also includes integration with Jira to automatically create tickets for any defects found during the testing process.

## Features:
- Real-time monitoring of automated tests
- Integration with Grafana for visualizing test results
- Integration with Jira for automatic ticket creation
- Customizable solution for different projects

## Installation:
1. Clone the repository: `git clone https://github.com/juacuegut/TFP_UPC_Real-Time_Monitoring_Testing.git`
2. Install Docker Compose: [Docker Compose Installation Guide](https://docs.docker.com/compose/install/)
3. Update the necessary configuration files under `.env`:
  - HOST_IP: The IP address of the host you will be using.
  - JIRA_URL: The URL of the Jira instance you want to connect to, for example, https://yourjira.atlassian.net. Replace "yourjira" with the corresponding subdomain of your Jira instance.
  - JIRA_USER: Your Jira username or email address.
  - JIRA_TOKEN: The Jira access token that needs to be generated. This token replaces the use of your Jira account password to authenticate API requests. You can generate an access token by following the steps provided by Atlassian at https://support.atlassian.com/atlassian-account/docs/manage-api-tokens-for-your-atlassian-account/.
  - JIRA_PROJECT: The abbreviation or key of the project in Jira to which you want to associate the issues or problems.
4. Build and run the Docker containers using Docker Compose: `docker-compose up`
5. Run the tests by executing the `testng.xml` file

## Usage:
1. Access the Grafana dashboard on `http://localhost:3000` to monitor the test results in real-time.
2. Any defects found during the testing process will be automatically created as tickets in Jira.

Thank you for your interest in this project! We hope it proves to be a valuable resource for real-time monitoring and testing.
