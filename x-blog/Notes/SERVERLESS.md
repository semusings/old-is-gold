# Table of Contents:

### Introduction
- Introduction to course
- AWS Lambda
- Serverless framework
- Managing permissions for the Serverless framework

### Building API with API Gateway and Lambda
- Creating landing page
- Creating the restaurants API
- Displaying restaurants in the landing page
- How to secure APIs in API Gateway
- Securing get restaurants endpoint with IAM-authorization
- Cognito
- Securing API endpoints with Cognito User Pools
- Leading practices for API Gateway
- Summary

### Testing
- AWS Lambda requires a different approach to testing
- Writing integration tests
- Writing acceptance tests
- BONUS What can possibly go wrong?

### Debugging
- Running and debugging functions locally with the Serverless framework
- Running and debugging functions locally with SAM local
- Serverless framework vs SAM local

### CI/CD
- What is CI/CD?
- Setup a CI/CD pipeline for deploying Lambda functions
- Troubleshoot AWS CodeBuild problems
- Summary

### Project Organization Tips
- Few monolithic functions vs many single-purposed functions
- How should you organize your functions into repos?
- How should you managed shared infrastructures?
- Tips for more effective teamwork

### Process real-time events with Kinesis and Lambda
- Designing the order flow with events
- Implementing the place order step
- Implement the notify restaurant step
- Implement the accept order step
- Implement the notify user step
- Implement the fulfill order step
- Dealing with partial failures
- Implement partial failure retries with SNS
- Implement per-function IAM roles
- Leading practices for using Kinesis and Lambda
- Problems with the event-driven approach
- Summary

### Logging
- Push Logs from CloudWatch Logs to Elasticsearch
- Structured Logging
- Sample debug level logging

### Monitoring
- Serverless monitoring requires a different way of thinking
- Record custom metrics synchronously
- Record custom metrics asynchronously
- Record memory usage and billed duration as metrics
- Auto-create alarms for APIs
- Summary

### X-Ray
- AWS X-Ray 101
- Use X-Ray to trace Lambda executions
- Set up custom X-Ray traces in Lambda functions
- AWS X-Ray Limitations

### Correlation IDs
- Laying out the Plan
- Auto-capture incoming correlation IDs via HTTP headers
- Include all the captured correlation IDs in the logs
- Forward correlation IDs via HTTP headers
- Forward correlation IDs via Kinesis events
- Auto-capture incoming correlation IDs via Kinesis events
- Enable debug logging on individual user events
- Auto-capture incoming correlation IDs via SNS messages
- Forward correlation IDs via SNS messages
- Summary

### Performance
- Life cycle of a Lambda function
- Strategies to minimize cold starts
- Take advantage of container reuse for optimization
- Cost considerations

### Error Handling
- Lambda retry behaviours
- Lambda Limits
- Hard and soft limits for Lambda
- Other service limits
- How to request a soft limit raise

### Managing Configurations
- SSM Parameter Store 101
- Reference SSM parameters in serverless.yml
- Limitations with Lambda environment variables
- Use Middy middleware to load SSM parameters at runtime
- Secrets Manager 101

### VPC
- Configure Lambda functions access to VPC
- When to use VPC and why you mostly shouldn’t

### Canary deployments
- Why should we do canary deployments?
- How can we implement canary deployments?
- What are weighted alias?
- Introducing API Gateway canary releases