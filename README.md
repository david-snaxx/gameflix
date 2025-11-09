## Getting started

- Clone the repository to your desired directory with ```git clone https://github.com/david-snaxx/gameflix.git```

## Building the gameflix-backend jar & image

- Build the project jar with ```mvn clean package```
- Create a docker image with ```docker build -t gameflix-backend .```

## Running the gameflix-backend image

- Confirm the docker image exists with ```docker images```
- Start a container with ```docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=docker" gameflix-backend```


## Testing the docker container with Postman
The following are some endpoints that should be accessible with a running container:

- Register a user
  - POST: ```http://localhost:8080/users/register```
  - BODY: ```{
    "username": "test_username",
    "password": "test_password"
}```

- Get the user
  - GET: ```http://localhost:8080/users/test_username```

- Delete the user
  - DELETE: ```http://localhost:8080/users/delete```
  - BODY: ```{
    "username": "test_username",
    "password": "test_password"
}```