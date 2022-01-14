import sys
sys.path.append("../../lib")
sys.path.append("../../src")
sys.path.append("../agente_prosp")

import psa
from agente_prosp.agentearospector import AgenteProspector
from agente_prosp.controlo_delib.controlodelib import ControloDelib
from lib.plan.plan_pdm.planopdm import PlanoPDM as Planeador

psa.iniciar("amb/amb2.das")

planeador = Planeador()
controlo = ControloDelib(planeador)
agente = AgenteProspector(controlo)

psa.executar(agente)
