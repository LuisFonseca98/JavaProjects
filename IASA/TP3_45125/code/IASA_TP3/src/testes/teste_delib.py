import sys
sys.path.append("../../lib")
sys.path.append("../agente_prosp")
sys.path.append("../../src")
import psa
import pee
from agente_prosp.agentearospector import AgenteProspector
from agente_prosp.controlo_delib.controlodelib import ControloDelib
from lib.plan.plan_pee.planpee import PlanPEE as Planeador
from pee.melhorprim.procura_aa import ProcuraAA
from pee.melhorprim.procura_sofrega import ProcuraSofrega
from pee.melhorprim.procura_custo_unif import ProcuraCustoUnif


psa.iniciar("amb/amb2.das")

procuraAA = ProcuraAA()
procuraSof = ProcuraSofrega()
planeador = Planeador(procuraSof)
controlo = ControloDelib(planeador)
agente = AgenteProspector(controlo)

psa.executar(agente)
