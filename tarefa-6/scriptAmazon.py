import csv
import time
from bs4 import BeautifulSoup
from msedge.selenium_tools import Edge, EdgeOptions

def extract_page_info(soup):
    result = []
    result.append(soup.find(id='productTitle').text)
    try:
        aux = soup.find('span', 'a-price a-text-price a-size-medium')
        result.append(aux.find('span', 'a-offscreen').text)
    except:
        result.append('0')
    table = soup.find(id='productDetails_techSpec_section_1')
    flag = 0
    marca = ''
    modelo = ''
    for aux in table.find_all('tr'):
        key = aux.th.text
        if key == ' Marca ':
            marca = aux.td.text.replace(' ', '')[2:]
            if flag:
                break         
        elif key == ' Número do modelo ':
            modelo = aux.td.text.replace(' ', '')[2:]
            if flag:
                break
            flag = 1
    result.append(modelo)
    result.append(marca)
    return result

def extract_product(item, driver):
    atag = item.h2.a
    url = 'https://www.amazon.com.br/' + atag.get('href')
    driver.get(url)
    soup = BeautifulSoup(driver.page_source, 'html.parser')
    result = extract_page_info(soup)
    try:
        result.append(item.i.text)
    except AttributeError:
        result.append('')
    result.append(url)
    return result

def main():
    start = time.time()
    options = EdgeOptions()
    options.use_chromium = True
    driver = Edge(options=options)
    
    records = []
    url = "https://www.amazon.com.br/s?i=computers&rh=n%3A16364755011&fs=true&page={}&qid=1638489366&ref=sr_pg_{}"
    
    driver.get(url.format(1, 1))
    soup = BeautifulSoup(driver.page_source, 'html.parser')
    aux = soup.find('ul', 'a-pagination')
    nPag = int(aux.find_all('li')[-2].text)

    for page in range(1, nPag):
        print("pagina", page)
        driver.get(url.format(page, page))
        soup = BeautifulSoup(driver.page_source, 'html.parser')
        results = soup.find_all('div',{'data-component-type': 's-search-result'})
        
        for item in results:
            record = extract_product(item, driver)
            if record:
                records.append(record)
                
    with open('results.csv', 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        writer.writerow(['descricao', 'preco', 'modelo', 'marca' , 'rating', 'url'])
        writer.writerows(records)
    print("Crawling finalizado em: ", time.time() - start, " segundos")


if __name__ == "__main__":
    main()