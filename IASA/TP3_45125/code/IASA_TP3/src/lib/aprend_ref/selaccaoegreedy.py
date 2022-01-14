import sys
sys.path.append("../../../src")
from lib.aprend_ref.selaccao import SelAccao
from random import random, choice


class SelAccaoEGreedy(SelAccao):

    def __init__(self, mem_aprend, accoes, epsilon = 0.01):
        self.__mem_aprend = mem_aprend
        self.accoes = accoes
        self.__epsilon = epsilon

    """Retorna uma accao, com uma probabilidade epsilon para explorar, caso seja outra, reorna-se"""
    def selecionar_accao(self, s):
        valorAleatorio = random()
        if valorAleatorio < self.__epsilon:
            return self.explorar(s)#explorar
        else:
            return self.max_accao(s)#aproveitar

    """escolhe a ação que leva á melhor recompensa"""
    def max_accao(self, s):
        return max(self.accoes, key=lambda a: self.__mem_aprend.obter(s, a))

    """escolher uma açao que 
    permite explorar o mundo para melhorar a aprendizagem"""
    def explorar(self, s):
        return choice(self.accoes)
