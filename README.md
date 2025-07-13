Corrently working on translating the web app from Vue to React.

## Quick Start with Docker

The **DEMO** can be launched with a single command:

```bash
docker-compose up
```

This will build and start the application, making it accessible at http://localhost:8086.

First launch takes up about 1 minute.

## Features

- Create and manage plans
- Track progress on your plans
- Track the progress of your participants
- Share plans with other users

## Demo Database

The Docker image uses an in-memory H2 database that is automatically populated with demo data when the application
starts. This includes sample plans, steps, and user accounts.

### Demo Users

Two demo user accounts are pre-configured:

| Username         | Password |
|------------------|----------|
| matteo@email.com | parola   |
| bogdan@email.com | parola   |

You can use these accounts to explore the application's features.
