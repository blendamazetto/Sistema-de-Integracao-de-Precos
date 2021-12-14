import csv 
from bs4 import BeautifulSoup
from bs4.element import TemplateString
from msedge.selenium_tools import Edge, EdgeOptions
import time


def extract_url_notebook(item):

    try:
        url_notebook = item.a.get('href')
        url = 'https://www.kabum.com.br' + url_notebook
    except AttributeError:
        return None

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
    flag = False
    for page in range(1, 35):
        driver.get(url.format(page))
        soup = BeautifulSoup(driver.page_source, 'html.parser')
        results = soup.find_all('div',{'class': 'sc-ZOtfp ejgrcc productCard'})
        
        if flag:
            break
        for item in results:
            record = extract_url_notebook(item)
            if record:
                record2 = extract_info(record[2], driver)
                if record2:   
                    tupla = record + record2
                    records.append(tupla)
                    time.sleep(15)
                    continue
            flag = True
            break

        time.sleep(30)

    return records


def extract_info(url_str, driver):
  
    driver.get(url_str)
    soup = BeautifulSoup(driver.page_source, 'html.parser')

    try:
        info = soup.find(id= 'secaoInformacoesTecnicas')
    except:
        return None

    try:
        ps = info.find_all('p')
        marca = ps[1].text[9:]
        modelo = ps[2].text[10:]
    except:
        marca = ''
        modelo = ''

    try:
        section = soup.find(id= 'secaoAvaliacoes')
        rating = section.find('span').text
    except:
        rating = ''

    caracteristicas = (marca, modelo, rating)

    return caracteristicas


def main():

    lista2 = lista_urls('https://www.kabum.com.br/computadores/notebooks-ultrabooks/notebooks?page_number={}&page_size=20&facet_filters=&sort=most_searched')
    
    result = lista2

    with open('resultsKabum.csv', 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        writer.writerow(['preco', 'descricao', 'url', 'marca', 'modelo' ,'rating'])
        writer.writerows(result)

if __name__ == "__main__":
    main()

