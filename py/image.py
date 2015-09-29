import re  
import urllib  
from bs4 import BeautifulSoup
#coding=utf-8
  
def getHtml(url):  
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

def main():
        html=fun()
	soup=BeautifulSoup(html, 'html.parser')
	#print html
	#print soup.title
	#print soup.title.name
	#print soup.p
	div = soup.find_all('div',{'class': 'poi-tile-nodeal'})
	n=0
	for d in div:
	   cf = d.find('div', {'class':'basic cf'})
	   
	   a=cf.a
	   
	   print a.get_text()
	   
	   url = a.get('href').split('#')[0]
           print url

           tagList=d.find('div',{'class':'tag-list'})
           tags=tagList.find_all('a')
           for tag in tags:
               print tag.get_text()

           rate=d.find('div',{'class':'rate'})
           print rate.find('span',{'class':'num'}).get_text()

           money=d.find('div',{'class':'poi-tile__money'})
           avg = money.find('span',{'class':'price'})
           
           if avg is not None:
               print avg.get_text()
           
           pricef2=money.find('span',{'value'}).get_text()
           print pricef2.split(';')[-1]
	   n=n+1
	print n
	#lis=div.findAll('li', {'class': 'next'})

	pattern=r'href="(.*?)"'
	urlreg=re.compile(pattern)
	#for li in lis:
	 #   link=li.find('a')
	    
	  #  url=link['href']
	   # print url;

	#pattern=r'href="(.*?)"'
	#urlreg=re.compile(pattern)
	#urls=re.findall(urlreg,div)

	#for url in urls:
	 #   print url

	#for line in div:
	   # print line
	#print soup.prettify() 

main() 
