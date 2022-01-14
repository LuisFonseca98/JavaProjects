import sys

sys.path.append("../../lib")
sys.path.append("../agente_prosp")
sys.path.append("../../src")

import psa

from agente_prosp.agentearospector import AgenteProspector
from agente_prosp.controlo_react.controloreact import ControloReact
from agente_prosp.controlo_react.reaccoes.recolher import Recolher as Comportamento

psa.iniciar("amb/amb4.das")

psa.executar(AgenteProspector(ControloReact(Comportamento())))
