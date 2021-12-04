import csv 
from bs4 import BeautifulSoup
from bs4.element import TemplateString
from msedge.selenium_tools import Edge, EdgeOptions


def extract_url_notebook(item):

    try:
        url_notebook = item.a.get('href')
        url = 'https://www.kabum.com.br' + url_notebook
    except AttributeError:
        return

    try:
        price = item.find('span','sc-iAKWXU kSZuOL priceCard').text
    except AttributeError:
        price = ''
   
    try:
        descricao = item.a.h2.text
    except AttributeError:
        descricao = ''
    
    caracteristicas = (price, descricao, url)

    return caracteristicas


def lista_urls(url):
    options = EdgeOptions()
    options.use_chromium = True
    driver = Edge(options=options)
    
    records = []

    for page in range(1, 2):
        driver.get(url.format(page))
        soup = BeautifulSoup(driver.page_source, 'html.parser')
        results = soup.find_all('div',{'class': 'sc-ZOtfp ejgrcc productCard'})
        
        for item in results:
            record = extract_url_notebook(item)
            if record:
                record2 = extract_info(record[2])   
                tupla = record + record2
                records.append(tupla)

    return records


def extract_info(url_str):
    options = EdgeOptions()
    options.use_chromium = True
    driver = Edge(options=options)  
  
    driver.get(url_str)
    soup = BeautifulSoup(driver.page_source, 'html.parser')

    try:
        info = soup.find(id= 'secaoInformacoesTecnicas')
    except AttributeError:
        return

    try:
        ps = info.find_all('p')
        marca = ps[1].text[9:]
        modelo = ps[2].text[10:]
    except AttributeError:
        marca = ''
        modelo = ''
        return

    try:
        section = soup.find(id= 'secaoAvaliacoes')
        rating = section.find('span').text
    except AttributeError:
        rating = ''

    caracteristicas = (marca, modelo, rating)

    return caracteristicas


def main():

    lista = lista_urls('https://www.kabum.com.br/computadores/notebook-gamer?page_number={}&page_size=20&facet_filters=&sort=most_searched')
    lista2 = lista_urls('https://www.kabum.com.br/computadores/notebooks-ultrabooks/notebooks?page_number={}&page_size=20&facet_filters=&sort=most_searched')
    
    result = lista + lista2

    with open('results.csv', 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        writer.writerow(['preco', 'descricao', 'url', 'marca', 'modelo' ,'rating'])
        writer.writerows(result)

if __name__ == "__main__":
    main()

