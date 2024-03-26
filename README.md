# Apud

O programa Apud foi desenvolvido por Adriano Vergínio Sinigaglia (adrianosinigaglia07@gmail.com) sob a orientação de Taís Cristine Appel Colvero como parte do trabalho de conclusão de curso intitulado "Desenvolvimento de ferramenta para busca avançada em acervos de arquivos PDF" no curso de Análise e Desenvolvimento de Sistemas (ADS), no IFRS Campus Veranópolis.

## Resumo do Projeto

Desenvolvido pela Adobe nos anos 90, o Portable Document Format (PDF) é, desde 2008, padronizado pela ISO 32000-1. Com o crescente uso de documentos PDF, a criação de acervos desses documentos tornou-se comum, dificultando a busca eficaz em seus conteúdos. Assim, surgiu a necessidade de desenvolver ferramentas que facilitassem esse processo de busca. Utilizando a linguagem de programação Java, em conjunto com o framework Java Swing e a biblioteca Apache Tika, a ferramenta Apud foi criada para permitir uma busca avançada em acervos de documentos PDF, seguindo etapas de avaliação de ferramentas existentes, análise e projeto de software, implementação e posterior validação.

## Como Usar

Para utilizar o Apud, siga os passos abaixo:

1. **Selecionar a Pasta de Artigos**: Inicie apontando para o diretório onde o acervo de PDFs está localizado. Isso pode ser feito digitando o diretório no campo "Pasta de artigos" ou utilizando o ícone de uma pequena pasta para navegar até o diretório desejado.
   
2. **Leitura dos PDFs**: Após selecionar a pasta, você tem a opção de ativar a "Recursão nas pastas" para incluir arquivos em subpastas na leitura. Clique em "Ler PDFs" para iniciar a varredura dos documentos usando a biblioteca Apache Tika.

3. **Realização de Buscas**: Com os dados dos PDFs lidos, você pode iniciar a busca textual. No lado direito da interface é possível escolher entre busca por sinônimos, busca por expressões regulares e ajustar a "Combinação mínima" para definir o nível de precisão da busca.

4. **Visualização de Resultados**: Os resultados exibem em quais documentos a busca foi bem-sucedida, os termos encontrados, a frase onde estão localizados, e sua relevância. Um clique duplo em qualquer linha da tabela abre um diálogo detalhado, mostrando frases adjacentes e permitindo abrir o documento no visualizador de PDFs padrão.

5. **Histórico de Buscas**: As buscas realizadas são automaticamente salvas no menu "Termos de pesquisa", facilitando referências futuras.

## Contribuições

Por favor, sinta-se livre para enviar pull requests ou abrir issues para discussão.

## Licença

Este projeto está licenciado sob a Creative Commons Attribution-NonCommercial 4.0 International License - veja o arquivo LICENSE ou [visite](https://creativecommons.org/licenses/by-nc/4.0/) para mais detalhes.

## Agradecimentos

Agradecimentos especiais à Taís Cristine Appel Colvero pela orientação e ao IFRS Campus Veranópolis pelo suporte durante o desenvolvimento deste projeto.

[IFRS Campus Veranópolis](https://ifrs.edu.br/veranopolis/)