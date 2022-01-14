"""
Segredo destes mecanismos de generalizacao
memória com um determinado raio de generalização,
e tudo o que cai nesse raio é abstraido
"""


class MemoriaAprend():

    def actualizar(self, s, a, q):
        raise NotImplementedError("actualizar MemoriaAprend")

    def obter(self, s, a):
        raise NotImplementedError("obter MemoriaAprend")
