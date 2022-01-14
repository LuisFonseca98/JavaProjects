import sys
sys.path.append("../../../src")
from agente_prosp.controlo import Controlo


class ControloReact(Controlo):

    def __init__(self, comportamento):
        self.comportamento = comportamento

    def processar(self, percepcao):
        # ativa uma resposta consoante a percepcao
        resposta = self.comportamento.activar(percepcao)
        # processa a resposta retornando uma accao
        # --> class resposta getter accao
        if resposta:
            return resposta.accao
