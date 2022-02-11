
from bs4 import BeautifulSoup
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager

class AmazonCrawling:

    def __init__(self, flag, n = None):
        self.baseURL = "https://www.amazon.com.br/s?i=computers&rh=n%3A16364755011&fs=true&page={}&qid=1638489366&ref=sr_pg_{}"
        self.options = webdriver.ChromeOptions()
        self.options.use_chromium = True
        self.options.add_argument("headless")
        self.options.add_argument("disable-gpu")
        self.options.add_argument('log-level=3')
        if flag:
            self.proxy = 'http=85.26.146.169:80'
            self.options.add_argument(f'--proxy-server={self.proxy}')
        self.driver = webdriver.Chrome(ChromeDriverManager().install(), options=self.options)
        if n == None:
            self.driver.get(self.baseURL.format(1, 1))
            soup = BeautifulSoup(self.driver.page_source, 'html.parser')
            self.pageQuantity = int(soup.find('span', 's-pagination-item s-pagination-disabled').text)
        else:
            self.pageQuantity = n

    def extract_page_info(self, url):
        self.driver.get(url)
        soup = BeautifulSoup(self.driver.page_source, 'html.parser')
        result = {}
        try:
            result["descricao"] = soup.find(id='productTitle').text.strip()
        except:
            return None
        try:
            aux = soup.find('span', 'a-price aok-align-center priceToPay')
            result["preco"] = aux.find('span', 'a-price-whole').text + aux.find('span', 'a-price-fraction').text
        except:
            result["preco"] = ''
        try:
            table = soup.find(id='productDetails_techSpec_section_1')
            marca = ''
            modelo = ''
            for aux in table.find_all('tr'):
                key = aux.th.text
                if key == ' Marca ':
                    marca = aux.td.text.replace(' ', '')[2:]
                elif key == ' Número do modelo ':
                    modelo = aux.td.text.replace(' ', '')[2:]
            result["modelo"] = modelo
            result["marca"] = marca
        except:
            return None
        try:
            aux = soup.find('span', {'id' : 'acrPopover'})
            result["rating"] = aux.span.a.i.span.text
        except AttributeError:
            result["rating"] = ''
        return result

    def extract_urls(self):
        result = []

        for page in range(1, self.pageQuantity + 1):
            print("pagina", page)
            self.driver.get(self.baseURL.format(page, page))
            soup = BeautifulSoup(self.driver.page_source, 'html.parser')
            results = soup.find_all('div',{'data-component-type' : 's-search-result'})
            
            for item in results:
                atag = item.h2.a
                result.append('https://www.amazon.com.br/' + atag.get('href'))

        return result