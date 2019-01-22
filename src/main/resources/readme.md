configuration:

use an adapter to configure the security, 2 methods to override

with the Jwt implementation, we want to connect already knowing the credentials from another source, and of course already being known on server side.
the config set the authentication type, the routes and their requested roles
eventually the users and their roles (if not stored elsewhere)

the https should be used but is not in this app
Ant path syntax: https://stackoverflow.com/questions/2952196/learning-ant-path-style
the login form is not necessary in that case, the login path do not imply a following call to the original page as the login and registration is mandatory before any answer.

The authentication process:
the authentication filter will create a JWT encrypted with a secret key stored in the server. The authentication is ok if the credentials match the known data in the users DB. If so, the JWT is created with his signature and added to the response. This process is done in two methods: the auth attempt and the successful authentication.

Once authenticated with the Authentication filter, the authorization filter is called. It creates a Spring user and add it to the security context for later use in the aop process when the servlet calls the controler for example. This user is created by getting the JWT in the request and decrypting it with the secret key and parse it, then by checking the token data (if the key is ok, the whole encrypted token matches the key and the data, name and roles). The user is created if all these actions went well.

if the user is in the context, all the controlers methods can be accessed and the role tested to allow the call or reject it.
