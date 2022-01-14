import sys

sys.path.append("../../../lib")
sys.path.append("../../../src")

from lib.plan.modeloplan import ModeloPlan
from psa.util import dirmov
from agente_prosp.controlo_delib.operadormover import OperadorMover

"""
percepcao.imagem vem uma imagem cuja a chave é uma descricao e valor é elemento
percepcao.posicao, tuplo x,y
psa.elementos precisamos só do alvo e obstaculo
dirmov retorna uma lista de angulos [0, pi/4, pi/8}
"""


class ModeloMundo(ModeloPlan):

    def __init__(self):
        self.__alterado = True
        self.__elementos = None
        self.__estados = None
        self.__estado = None
        self.__operadores = [OperadorMover(self, ang) for ang in dirmov()]

    """estado e uma coordenada"""

    def obter_elem(self, estado):
        for key in self.__elementos.keys():
            if key == estado:
                return self.__elementos[key]

    def actualizar(self, percepcao):
        self.__estado = percepcao.posicao
        if self.__elementos != percepcao.imagem:
            self.__elementos = percepcao.imagem
            self.__estados = percepcao.imagem.keys()
            self.__alterado = True
        else:
            self.__alterado = False

    def operadores(self):
        return self.__operadores

    def estados(self):
        return self.__estados

    @property
    def estado(self):
        return self.__estado

    @property
    def alterado(self):
        return self.__alterado

    def mostrar(self, vis):
        vis.elementos({estado: self.__elementos.get(estado)
                       for estado in self.__elementos.keys()
                       if self.__elementos[estado] is not 'vazio'})
