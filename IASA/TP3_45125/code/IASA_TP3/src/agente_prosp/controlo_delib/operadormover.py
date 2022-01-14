import sys
sys.path.append("../../../lib")
from pee.modprob.operador import Operador
from psa.accao import Avancar
from psa.util import mover, dist
from psa.elementos import ALVO, OBST, VAZIO


class OperadorMover(Operador):

    def __init__(self, modelo_mundo, ang):
        self.modelo_mundo = modelo_mundo
        self.ang = ang
        self.__accao = Avancar(ang)

    def aplicar(self, estado):
        novo_estado = mover(estado, self.ang)
        opcoesValidas = [VAZIO, ALVO]
        elemento = self.modelo_mundo.obter_elem(novo_estado)

        if elemento in opcoesValidas:
            return novo_estado

    """Custo é proporcional a distancia com o valor minimo de 1"""
    def custo(self, estado, novo_estado):
        return max(dist(estado, novo_estado), 1)

    @property
    def accao(self):
        return self.__accao
