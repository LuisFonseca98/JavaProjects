import sys
sys.path.append("../lib/ecr")
sys.path.append("../agente_prosp")
sys.path.append("../../lib")

import psa

from psa.agente import Agente

from psa.actuador import ESQ
from psa.actuador import FRT
from psa.actuador import DIR

from psa.accao import Rodar
from psa.accao import Mover
from psa.accao import Avancar




class AgenteTeste(Agente):
    def executar(self):
        self.actuador.actuar(Avancar())


psa.iniciar("amb/amb1.das")

psa.executar(AgenteTeste())
