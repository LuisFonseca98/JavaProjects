import sys
sys.path.append("../../../lib")
sys.path.append("../../../src")
from lib.aprend_ref.memoriaesparsa import MemoriaEsparsa
from lib.aprend_ref.selaccaoegreedy import SelAccaoEGreedy
from lib.aprend_ref.aprendq import AprendQ
import psa


class MecAprend:

    def __init__(self, accoes):
        self.accoes = accoes
        self.__mem_aprend = MemoriaEsparsa()
        self.__sel_accao = SelAccaoEGreedy(self.__mem_aprend, accoes)
        self.__aprend_ref = AprendQ(self.__mem_aprend, self.__sel_accao)

    """Atualiza a memoria Q"""
    def aprender(self, s, a, r, sn):
        self.__aprend_ref.aprender(s, a, r, sn)

    """Seleciona a accao a realizar(aproveitar ou explorar)"""

    def selecionar_accao(self, s):
        return self.__sel_accao.selecionar_accao(s)

    @property
    def _mem_aprend(self):
        return self.__mem_aprend

    @property
    def _sel_accao(self):
        return self.__sel_accao

    def mostrar(self):
        psa.vis(1).limpar()
        psa.vis.aprendref(self.__aprend_ref)
