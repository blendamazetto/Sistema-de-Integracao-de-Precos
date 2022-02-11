
from msilib.schema import tables
from bs4 import BeautifulSoup
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager

class MagaluCrawling:

    def __init__(self, flag, n = None):
        self.baseURL = "https://www.magazineluiza.com.br/notebook/informatica/s/in/note?page={}"
        self.options = webdriver.ChromeOptions()
        self.options.use_chromium = True
        #self.options.add_argument("headless")
        self.options.add_argument("disable-gpu")
        self.options.add_argument('log-level=3')
        if flag:
            self.proxy = 'http=85.26.146.169:80'
            self.options.add_argument(f'--proxy-server={self.proxy}')
        self.driver = webdriver.Chrome(ChromeDriverManager().install(), options=self.options)
        if n == None:
            self.driver.get(self.baseURL.format(1, 1))
            soup = BeautifulSoup(self.driver.page_source, 'html.parser')
            aux = soup.find('ul', {'class', 'css-9j990n'}).find_all('li')
            self.pageQuantity = int(aux[-2].a.text)
        else:
            self.pageQuantity = n

    def extract_page_info(self, url):
        self.driver.get(url)
        soup = BeautifulSoup(self.driver.page_source, 'html.parser')
        result = {}
        try:
            result["descricao"] = soup.find('h1', {'class' : 'header-product__title'}).text
        except:
            return None
        try:
            result["preco"] = soup.find('span', {'class' : 'price-template__text'}).text
        except:
            result["preco"] = ''
        try:
            keys = soup.find_all('td', {'class' : 'description__information-left'})
            values = soup.find_all('td', {'class' : 'description__information-box-right'})
            marca = ''
            modelo = ''
            for i in range(len(keys)):
                key = keys[i].text
                if key == 'Marca':
                    marca = values[i].text
                elif key == 'Modelo':
                    modelo = values[i].text
            result["modelo"] = modelo
            result["marca"] = marca
        except:
            return None
        try:
            result["rating"] = soup.find('span', {'class' : 'js-rating-value'}).text
        except AttributeError:
            result["rating"] = ''
        return result

    def extract_urls(self):
        result = []

        for page in range(1, self.pageQuantity + 1):
            print("pagina", page)
            self.driver.get(self.baseURL.format(page))
            soup = BeautifulSoup(self.driver.page_source, 'html.parser')
            aux = soup.find(id='showcase').find_all('ul')

            for item in aux[0].find_all('a', {'name' : 'linkToProduct'}):
                result.append(item.get('href'))
       
        return result