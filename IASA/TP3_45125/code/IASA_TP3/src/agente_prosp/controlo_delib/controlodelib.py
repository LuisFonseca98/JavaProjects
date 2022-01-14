import sys
sys.path.append("../../../lib")
sys.path.append("../../../src")
from agente_prosp.controlo_delib.modelomundo import ModeloMundo
from agente_prosp.controlo import Controlo
from psa.elementos import ALVO
import psa


class ControloDelib(Controlo):

    def __init__(self, planeador):
        self.__planeador = planeador
        self.__modelo_mundo = ModeloMundo()
        self.__objectivos = None#sendo deliberativo é necessário criar

    """indica se é necessário voltar a deliberar e planear"""
    def __reconsiderar(self):
         return not self.__objectivos \
                or not self.__planeador.plano_pendente() \
                or self.__modelo_mundo.alterado

    """raciocionio acerca dos fins - gera os objetivos"""
    def __deliberar(self):
        self.__objectivos = [estado for estado in self.__modelo_mundo.estados()
               if self.__modelo_mundo.obter_elem(estado) == ALVO]

    """usa o planeador"""
    def __planear(self):
        if self.__objectivos:
            self.__planeador.planear(self.__modelo_mundo,
                                     self.__modelo_mundo.estado,
                                     self.__objectivos)
        else:
            self.__planeador.terminar_plano()

    """usa o planeador"""
    def __executar(self):
        operador = self.__planeador.obter_accao(self.__modelo_mundo.estado)

        if operador:
            return operador.accao

    def processar(self,percepcao):
        self.__assimilar(percepcao)
        if self.__reconsiderar():
            self.__deliberar()
            self.__planear()
        #self.mostrar()
        return self.__executar()

    def __assimilar(self, percepcao):
        self.__modelo_mundo.actualizar(percepcao)

    # def mostrar(self):
    #     vis = psa.vis(1)
    #     vis.limpar()
    #     self.__planeador.mostrar(vis, self.__modelo_mundo.estado)
