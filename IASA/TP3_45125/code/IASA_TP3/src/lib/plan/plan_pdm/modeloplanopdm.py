import sys
sys.path.append("../../../../src")
from lib.pdm.modelopdm import ModeloPDM
from lib.plan.modeloplan import ModeloPlan


class ModeloPDMPlan(ModeloPDM,ModeloPlan):

    def __init__(self, modelo_plan, objetivos):

        self.__rmax = 100
        self.__objetivos = objetivos
        self.__modelo_plan = modelo_plan

    def estados(self):
        return self.__modelo_plan.estados()

    def operadores(self):
        return self.__modelo_plan.operadores()

    def S(self):
        return self.estados()

    def A(self, s):
        return self.operadores()

    def T(self, s, a):
        sn = a.aplicar(s)  # estado seguinte
        if sn is not None:
            return [(1, sn)]  # modelo determinista
        # se a transiçao nao ocorrer
        return []

    # recompensa de uma transição
    def R(self, s, a, sn):
        # perda (sinal negativo) = custo da açao
        # se o proximo estado for objetivo
        if sn in self.__objetivos:
            return self.__rmax - a.custo(s,sn)
        return - a.custo(s,sn)
