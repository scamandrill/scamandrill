package io.github.scamandrill.client

import akka.actor.ActorSystem

class Scamandrill(val system: ActorSystem = ActorSystem("scamandrill")) extends MandrillClientProvider {}