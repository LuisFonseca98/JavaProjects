
class ModeloPDM():

    def S(self):#retorna lista<Estado>
        raise NotImplementedError("S ModeloPDM")

    def A(self, estado):#list<Operador>
        raise NotImplementedError("A ModeloPDM")

    def T(self, s, a):#list<Transicao>
        #transicao -> tuplo(accao, estado_sucessor)
        raise NotImplementedError("T ModeloPDM")

    def R(self, s, a, sn):#double
        raise NotImplementedError("R ModeloPDM")
