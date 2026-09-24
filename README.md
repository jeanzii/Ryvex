# \# Ryvex

# 

# \*\*Build. Trade. Track.\*\*

# 

# Ryvex is a desktop application designed to bring PC building, hardware trading, PC flipping, and personal finance tracking into one centralized platform.

# 

# The project is being developed as a modular desktop application with a JavaFX frontend, a Spring Boot backend, and PostgreSQL for persistent data storage.

# 

# \---

# 

# \## Features

# 

# Ryvex is planned to include:

# 

# \* User registration and authentication

# \* Secure login sessions

# \* User profiles

# \* Dashboard

# \* PC Builder

# \* Hardware Marketplace

# \* PC Flipping tools

# \* Personal Finance tracking

# \* Administrative tools

# \* Hardware and build tracking

# \* Future cloud-connected functionality

# 

# \---

# 

# \## Current Development Status

# 

# \### Stage 1 — Project Foundation

# 

# Completed:

# 

# \* Git repository setup

# \* JavaFX client project

# \* Spring Boot backend project

# \* Client-to-server communication

# \* `/api/status` health endpoint

# \* PostgreSQL development database

# \* Spring Data JPA integration

# \* Local development configuration

# \* Secure handling of local credentials

# 

# \### Stage 2 — Accounts \& Authentication

# 

# Completed:

# 

# \* User database model

# \* User roles

# \* User repository

# \* Account registration

# \* Username and email validation

# \* Password hashing

# \* Login with username or email

# \* JWT access tokens

# \* Protected API endpoints

# \* Refresh tokens

# \* Refresh-token rotation

# \* Logout and token revocation

# \* `/api/auth/me` authenticated user endpoint

# 

# \### Stage 3 — Application Interface

# 

# Next:

# 

# \* Ryvex application shell

# \* Midnight UI theme

# \* Neon-purple accent system

# \* Ryvex branding integration

# \* Sidebar navigation

# \* Dashboard

# \* Login and registration interface

# \* Client-side authenticated session handling

# 

# \---

# 

# \## Tech Stack

# 

# \### Desktop Client

# 

# \* Java 25

# \* JavaFX

# \* Maven

# \* Java HTTP Client

# 

# \### Backend

# 

# \* Java 25

# \* Spring Boot

# \* Spring Web

# \* Spring Data JPA

# \* Spring Security

# \* JWT authentication

# \* Hibernate

# \* Maven

# 

# \### Database

# 

# \* PostgreSQL

# \* DataGrip for database development and management

# 

# \### Development Tools

# 

# \* IntelliJ IDEA

# \* Git

# \* GitHub

# 

# \---

# 

# \## Project Structure

# 

# ```text

# Ryvex/

# │

# ├── ryvex-client/

# │   ├── src/

# │   └── pom.xml

# │

# ├── ryvex-server/

# │   ├── src/

# │   └── pom.xml

# │

# ├── assets/

# │

# ├── database/

# │

# ├── docs/

# │

# ├── .gitignore

# │

# └── README.md

# ```

# 

# \### `ryvex-client`

# 

# Contains the JavaFX desktop application.

# 

# The client communicates with the Ryvex backend using HTTP requests.

# 

# \### `ryvex-server`

# 

# Contains the Spring Boot REST API.

# 

# The server handles:

# 

# \* Authentication

# \* Business logic

# \* Database communication

# \* User management

# \* Future Ryvex services

# 

# \### `database`

# 

# Reserved for database documentation, diagrams, migration resources, and other database-related files.

# 

# \### `assets`

# 

# Contains Ryvex branding resources such as logos, application icons, and promotional assets.

# 

# \### `docs`

# 

# Contains project documentation, architecture notes, development plans, and technical references.

# 

# \---

# 

# \## Development Environment

# 

# Ryvex currently uses:

# 

# ```text

# Java / JDK: 25

# Backend Port: 8080

# PostgreSQL Port: 5432

# Database: ryvex

# ```

# 

# The backend can be checked through:

# 

# ```text

# GET http://localhost:8080/api/status

# ```

# 

# A successful response returns:

# 

# ```json

# {

# &#x20; "status": "online",

# &#x20; "application": "Ryvex"

# }

# ```

# 

# \---

# 

# \## Local Database Setup

# 

# Ryvex uses PostgreSQL for local development.

# 

# Recommended configuration:

# 

# ```text

# Host: localhost

# Port: 5432

# Database: ryvex

# Application User: ryvex\_app

# ```

# 

# Database credentials are stored locally and must never be committed to Git.

# 

# Create:

# 

# ```text

# ryvex-server/src/main/resources/application-local.properties

# ```

# 

# Example:

# 

# ```properties

# spring.datasource.url=jdbc:postgresql://localhost:5432/ryvex

# spring.datasource.username=ryvex\_app

# spring.datasource.password=YOUR\_PASSWORD

# 

# spring.jpa.hibernate.ddl-auto=update

# spring.jpa.show-sql=true

# spring.jpa.properties.hibernate.format\_sql=true

# spring.jpa.open-in-view=false

# 

# ryvex.jwt.secret=YOUR\_SECRET

# ryvex.jwt.access-token-minutes=30

# ryvex.jwt.refresh-token-days=30

# ```

# 

# `application-local.properties` is excluded from Git and should remain local to each development machine.

# 

# Activate the local Spring profile with:

# 

# ```text

# SPRING\_PROFILES\_ACTIVE=local

# ```

# 

# \---

# 

# \## Running the Backend

# 

# Open the `ryvex-server` project and run:

# 

# ```text

# RyvexServerApplication

# ```

# 

# The server should start at:

# 

# ```text

# http://localhost:8080

# ```

# 

# You should see:

# 

# ```text

# Started RyvexServerApplication

# ```

# 

# in the console.

# 

# \---

# 

# \## Running the Desktop Client

# 

# Start the backend first.

# 

# Then run:

# 

# ```text

# RyvexApplication

# ```

# 

# from the `ryvex-client` project.

# 

# The client currently verifies that the backend is reachable and can display:

# 

# ```text

# ● Ryvex services online

# ```

# 

# \---

# 

# \## Authentication

# 

# Ryvex currently supports:

# 

# ```text

# POST /api/auth/register

# POST /api/auth/login

# POST /api/auth/refresh

# POST /api/auth/logout

# GET  /api/auth/me

# ```

# 

# Public endpoints:

# 

# ```text

# GET  /api/status

# POST /api/auth/register

# POST /api/auth/login

# POST /api/auth/refresh

# POST /api/auth/logout

# ```

# 

# Protected endpoints require:

# 

# ```text

# Authorization: Bearer <access-token>

# ```

# 

# Passwords are never stored directly.

# 

# Ryvex stores secure password hashes and uses signed JWT access tokens together with server-side refresh-token records.

# 

# Refresh tokens are rotated when used, allowing old refresh tokens to become invalid immediately.

# 

# \---

# 

# \## Security

# 

# Sensitive information must never be committed to the repository.

# 

# This includes:

# 

# \* PostgreSQL passwords

# \* JWT signing secrets

# \* API keys

# \* Access tokens

# \* Refresh tokens

# \* Local environment configuration

# 

# Local configuration files such as:

# 

# ```text

# application-local.properties

# .env

# ```

# 

# are excluded through `.gitignore`.

# 

# \---

# 

# \## Git Workflow

# 

# Before starting work:

# 

# ```bash

# git pull

# ```

# 

# After completing a development milestone:

# 

# ```bash

# git add .

# git commit -m "Describe the completed work"

# git push

# ```

# 

# Always push changes before switching development machines and pull the latest changes before continuing work elsewhere.

# 

# \---

# 

# \## Design Direction

# 

# Ryvex uses a premium dark interface inspired by modern desktop software.

# 

# \### Primary visual identity

# 

# \* Midnight / near-black background

# \* Deep navy surfaces

# \* Neon-purple accents

# \* Subtle purple glow

# \* Light lavender and white typography

# 

# The application will use Ryvex branding consistently across:

# 

# \* Desktop application icon

# \* In-app logo

# \* Splash and branding screens

# \* Navigation

# \* Buttons

# \* Active states

# \* Charts and highlights

# 

# \---

# 

# \## Roadmap

# 

# Planned development stages include:

# 

# 1\. Project Foundation

# 2\. Accounts \& Authentication

# 3\. Main Application Interface

# 4\. Dashboard

# 5\. PC Builder

# 6\. PC Flipping

# 7\. Finance

# 8\. Marketplace

# 9\. Profiles

# 10\. Administration

# 11\. Packaging and Distribution

# 12\. Online Deployment and Production Services

# 

# \---

# 

# \## Project Status

# 

# Ryvex is currently under active development.

# 

# The backend foundation and authentication system are functional. Development is now moving toward the main JavaFX application interface and user experience.

# 

# \---

# 

# \## License

# 

# A license has not yet been selected.

# 

# Until a license is added, all rights to the Ryvex source code and branding are reserved by the project owner.



