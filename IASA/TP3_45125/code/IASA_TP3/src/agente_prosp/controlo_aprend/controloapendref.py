import sys
sys.path.append("../../../lib")
sys.path.append("../../../src")

from agente_prosp.controlo import Controlo
from agente_prosp.controlo_aprend.mecaprend import MecAprend
from psa.actuador import DIR, ESQ, FRT
from psa.util import dirmov
import psa


class ControloAprendRef(Controlo):

    def __init__(self):
        self.__rmax = 100
        self.__s = None
        self.__a = None
        self.__mec_aprend = MecAprend([psa.actuador.Avancar(ang) for ang in dirmov()])

    """Agente reativo que se programa a si proprio"""
    def processar(self, percepcao):
        sn = percepcao.posicao
        reforco = self.__gerar_reforco(percepcao)

        if self.__s is not None:
            self.__mec_aprend.aprender(self.__s, self.__a, reforco, sn)

        an = self.__mec_aprend.selecionar_accao(sn)
        self.__a = an
        self.__s = sn

        self.mostrar()

        return self.__a

    def __gerar_reforco(self, percepcao):
        reforco = -percepcao.custo_mov
        if percepcao.carga:
            reforco = self.__rmax
        elif percepcao.colisao:
            reforco = -self.__rmax
        return reforco

    '''chamamos antes de retornar a acao'''
    def mostrar(self):
        psa.vis(1).limpar()
        psa.vis(1).aprendref(self.__mec_aprend)
