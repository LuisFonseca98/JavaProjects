import sys
sys.path.append("../../../../src")
from lib.plan.planeador import Planeador
from lib.plan.plan_pee.problemaplan import ProblemaPlan


class PlanPEE(Planeador):

    def __init__(self, mec_pee):
        # qualquer procura larg,prof,profit,AA,sofrega,custounif
        self.__mec_pee = mec_pee
        # plano e um conjunto de operadores que fazem parte de uma soluçao
        # visto que as solucoes do agente sao movimentos no espaco AMB
        self.__plano = None

    def planear(self, modelo_plan, estado_inicial, objectivos):
        # metodo utilizado no planear do controlo delib, de modo a obter um conjunto de
        # acoes/operadores
        estado_final = objectivos[0]
        print(estado_final)
        operadores = modelo_plan.operadores()
        problema = ProblemaPlan(estado_inicial, estado_final, operadores)
        # conjunto de passos_solucao que na verdade sao operadores com accoes
        solucao = self.__mec_pee.resolver(problema)
        self.__plano = [passo_solucao.operador for passo_solucao in solucao[1:] if solucao]

    def obter_accao(self, estado):
        if self.__plano:
           return self.__plano.pop(0)

    def plano_pendente(self):
        # se nao existem operadores no plano, nao existem planos de acoes pendentes
        return self.__plano

    def terminar_plano(self):
        # termina plano, passam a nao existir operadores
        self.__plano = None

    # # metodo auxiliares
    # def mostrar(self, estado, vis):
    #     vis.plano(estado, self.__plano)
    #     vis.marcar([estado], linha=1)
