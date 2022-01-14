import sys
sys.path.append("../../../../src")
from lib.aprend_ref.aprendref import AprendRef


class AprendQ(AprendRef):

    def __init__(self, mem_aprend, sel_accao, alfa=0.5, gama=0.9):
        super().__init__(mem_aprend, sel_accao)
        self._alfa = alfa
        self._gama = gama

    def aprender(self, s, a, r, sn):
        Qatual = self._mem_aprend.obter(s, a)# obtem o valor da memoria q
        nextA = self._sel_accao.max_accao(sn)# obtem a acção do proximo estado
        nextQ = self._mem_aprend.obter(sn, nextA) # obtem o valor da memoria no e
        finalQ = Qatual + self._alfa * (r + self._gama * nextQ - Qatual)
        self._mem_aprend.actualizar(s, a, finalQ)# atualiza a memoria -> mem esparsa
