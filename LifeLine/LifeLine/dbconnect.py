import pymysql
def iud(q,val):
    con=pymysql.connect(host='localhost',port=3306,user='root',passwd='',db='lifeline')
    cmd=con.cursor()
    cmd.execute(q,val)
    id=cmd.lastrowid
    con.commit()
    return id


def select(q):
    con = pymysql.connect(host='localhost', port=3306, user='root', passwd='', db='lifeline')
    cmd = con.cursor()
    cmd.execute(q)
    s=cmd.fetchall()
    return s
def selectall(q,val):
    con = pymysql.connect(host='localhost', port=3306, user='root', passwd='', db='lifeline')
    cmd = con.cursor()
    cmd.execute(q,val)
    s = cmd.fetchall()
    return s

def selectone(q,val):
    con = pymysql.connect(host='localhost', port=3306, user='root', passwd='', db='lifeline')
    cmd = con.cursor()
    cmd.execute(q,val)
    s = cmd.fetchone()
    return s


def androselectall(q):
    con = pymysql.connect(host='localhost', port=3306, user='root', passwd='', db='lifeline')
    cmd = con.cursor()
    cmd.execute(q)
    s = cmd.fetchall()
    print(s)
    row_headers = [x[0] for x in cmd.description]
    json_data = []
    for result in s:
        json_data.append(dict(zip(row_headers, result)))
    return (json_data)

def androselectalls(q,val):
    con = pymysql.connect(host='localhost', port=3306, user='root', passwd='root', db='lifeline')
    cmd = con.cursor()
    cmd.execute(q,val)
    s = cmd.fetchall()
    print(s)
    row_headers = [x[0] for x in cmd.description]
    json_data = []
    for result in s:
        json_data.append(dict(zip(row_headers, result)))
    return (json_data)

