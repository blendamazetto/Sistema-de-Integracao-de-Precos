import csv
import time
from bs4 import BeautifulSoup
from selenium import webdriver

def extract_page_info(soup):
    result = []
    try:
        result.append(soup.find('h1', 'product-title__Title-sc-1hlrxcw-0').text)
    except:
        print("erro descrição")
        return None
    try:
        result.append(soup.find('div', 'src__BestPrice-sc-1jvw02c-5 cBWOIB priceSales').text)
    except:
        result.append('')
    try:
        table = soup.find_all('tr', 'src__View-sc-70o4ee-6 fWIYEf')
        marca = ''
        modelo = ''
        for aux in table:
            esp = aux.find_all('td')
            key = esp[0].text
            value = esp[1].text
            if (key == 'Marca' or key == 'Fabricante') and marca == '':
                marca = value      
            elif (key == 'Referência do Modelo' or key == 'Modelo') and modelo == '':
                modelo = value
        result.append(modelo)
        result.append(marca)
    except:
        result.append('')
        result.append('')
    try:
        result.append(soup.find('span', 'header__RatingValue-sc-1cmv5xe-9 dkRAzF').text)
    except:
        result.append('')
    return result

def extract_product(item, driver):
    atag = item.a
    url = 'https://www.americanas.com.br' + atag.get('href')
    driver.get(url)
    soup = BeautifulSoup(driver.page_source, 'html.parser')
    result = extract_page_info(soup)
    if result:
        result.append(url)
    return result

def main():
    start = time.time()
    options = webdriver.ChromeOptions()
    options.use_chromium = True
    options.add_argument('user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36')
    driver = webdriver.Chrome(options=options)
    records = []
    url = "https://www.americanas.com.br/categoria/informatica/notebooks?chave=pfm_hm_tt_1_0_notebook&limit=24&offset={}"
    
    driver.get(url.format(0))
    soup = BeautifulSoup(driver.page_source, 'html.parser')
    aux = soup.find('span', 'grid-area__TotalText-sc-1d1d4iv-1').text
    nP = int(aux.split()[0].replace('.', ''))
    flag = 0
    for n in range(0, nP + 24, 24):
        if flag:
            break
        print("pagina", int(n // 24))
        driver.get(url.format(n))
        soup = BeautifulSoup(driver.page_source, 'html.parser')
        results = soup.find_all('div', 'src__Wrapper-sc-1k0ejj6-3 eflURh')
        for item in results:
            record = extract_product(item, driver)
            time.sleep(1)
            if record:
                records.append(record)
            else:
                flag = 1
                break
        time.sleep(20)
                
    with open('resultsAmericanas.csv', 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        writer.writerow(['descricao', 'preco', 'modelo', 'marca' , 'rating', 'url'])
        writer.writerows(records)
    print("Crawling finalizado em: ", time.time() - start, " segundos")


if __name__ == "__main__":
    main()