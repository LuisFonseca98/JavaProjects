import sys
sys.path.append("../../../../lib")
sys.path.append("../../../../src")

from lib.plan.plan_pdm.modeloplanopdm import ModeloPDMPlan
from lib.plan.planeador import Planeador
from lib.pdm.pdm import PDM
import psa


class PlanoPDM(Planeador):

    def __init__(self):
        self.__gama = 0.95
        self.__delta_max = 1.0
        self.__utilidade = None
        self.__politica = None
        self.__pdm = PDM(self.__gama, self.__delta_max)

    def planear(self, modelo_plan, estado_inicial, objectivos):
        modelo = ModeloPDMPlan(modelo_plan, objectivos)
        self.__utilidade, self.__politica = self.__pdm.resolver(modelo)
        if not self.__politica:
            self.terminar_plano()

    def obter_accao(self, s):
        # s estado
        # ir a utilidade ver quala a accao
        # devolve accao para aquele estado
        # e como apenas estamos assumir uma transicao
        if self.__politica:
            return self.__politica.get(s)

    def plano_pendente(self):
        return self.__politica

    def terminar_plano(self):
        # termina um plano
        self.__politica = None

    # metodo auxiliar
    def mostrar(self, estado, vis):
        if self.__politica:
            vis.campo(self.__utilidade)
            vis.politica(self.__politica)
