

class Planeador():

    def __init__(self,modelo_plan,estado_inicial,objectivos):
        raise NotImplementedError("planear Planear")

    def obter_accao(self,estado):
        raise NotImplementedError("obter_accao Planeador")

    def plano_pendente(self):
        raise NotImplementedError("plano_pendente Planeador")

    def terminar_plano(self):
        raise NotImplementedError("terminar_plano Planeador")
