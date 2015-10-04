#coding=utf-8
import json
import urlparse
import re  
import sys,codecs
import urllib  
from bs4 import BeautifulSoup
  
def getHtml(url):  
    print url
    page = urllib.urlopen(url)  
    html = page.read()  
    return html  
  
def getImg(html):  
    reg = r'src="(.*?\.jpg)" pic_ext'  
    imgre = re.compile(reg)  
    imglist = re.findall(imgre,html)  
    x=0  
    for imgurl in imglist:  
        urllib.urlretrieve(imgurl,'%s.jpg' % x)  
	print imgurl
        x+=1  
    return x;  

def getItem(html):
    reg1 = r'\<div class="menu__items"\>\<table.*?\>(.*?)\</table\>';
    reg = r'td class=".*?"\>(.*?)\<span';
    itemsre = re.compile(reg1)
    itemslist=re.findall(itemsre,html);

    itemre = re.compile(reg)
    x=0
    for items in itemslist:
         itemlist=re.findall(itemre,items)
         
         for item in itemlist:
             x+=1
             print item

    return x

def getShangJia(html):
    reg=r'link f3".*?href="(.*?)#smh:.*?\</a\>'
    urlreg=re.compile(reg)
    urllist=re.findall(urlreg,html)
    x=0    
    for url in urllist:
        #print url[0]+' '+unicode(url[1], 'gbk');
        print url
        x+=1
    return x

def fun():
	#html = getHtml("http://tieba.baidu.com/p/2460150866")  
	#html = getHtml("http://www.meituan.com/shop/18620")

	#html = getHtml("http://hz.meituan.com/shop/707799")
	html=getHtml("http://hz.meituan.com/category/meishi")
		#print html
	return html
	#print getShangJia(html)
	#print getItem(html)

def convert(str):
	if not isinstance(str, unicode):
		str = unicode(str, 'utf-8')
	return str

def getNextPage(html):
	soup=BeautifulSoup(html,'html.parser')
	paginator = soup.find('div',{'class':'paginator-wrapper'})
	next = paginator.find('li',{'class':'next'})
	if next==None:
		return None;

	url = next.a.get('href')
	baseurl='http://hz.meituan.com/category/meishi'
	return urlparse.urljoin(baseurl, url);

def func():
	html=fun()
	print getNextPage(html)

def saveToJSON(merchants):
	output = codecs.open('merchants.info','w')
	output.writelines(json.dumps(merchants, ensure_ascii=False, indent=4).encode('utf-8'))
	output.close()
	return	
	
def crawleMerchantInfo(html, merchants):
	soup=BeautifulSoup(html, 'html.parser')

	div = soup.find_all('div',{'class': 'poi-tile-nodeal'})
	
	for d in div:
		cf = d.find('div', {'class':'basic cf'})
		
		merchant = {}	
		merchant['name'] = convert(cf.a.get_text().strip());
		merchant['url']=cf.a.get('href').split('#')[0].strip()

		tagList=d.find('div',{'class':'tag-list'})
		tags=[]
		for tag in tagList.find_all('a'):
			tags.append(convert(tag.get_text().strip()))

		merchant['tag'] = tags;
		
		rate= d.find('div',{'class':'rate'}).find('span',{'class':'num'}).get_text().strip()
		merchant['rate'] = int(rate);

		money = d.find('div',{'class':'poi-tile__money'})
		avg = money.find('span',{'class':'price'})

		if avg is not None:
			average= convert(avg.get_text().strip())
			merchant['avg']=average

		price= money.find('span',{'value'}).get_text().split(';')[-1]

		merchant['price'] = price
		
		merchants.append(merchant);
	
	
	
def main():
	
	url='http://hz.meituan.com/category/meishi'
	merchants=[]

	while url is not None:
		html = getHtml(url)
		crawleMerchantInfo(html, merchants)
		url = getNextPage(html)

	saveToJSON(merchants)
main() 
