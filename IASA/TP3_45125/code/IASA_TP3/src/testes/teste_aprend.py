import sys
sys.path.append("../../lib")
sys.path.append("../agente_prosp")
sys.path.append("../../src")

import psa
from agente_prosp.agentearospector import AgenteProspector
from agente_prosp.controlo_aprend.controloapendref import ControloAprendRef as Controlo

psa.iniciar("amb/amb6.das",reiniciar = True)

agente = AgenteProspector(Controlo())

psa.executar(agente)
