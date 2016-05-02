
_gardengnome_ provides a simple RSB service for querying a MongoDB database containing user profiles. It requires a running `mongod --dbpath ./data/db/` instance.

Starting it with `factoryreset` as argument will reset the underlying database with the data specified in `src/main/resources/data`.
Currently, it also provides a REST service.

## Show basic information about the database

```
curl -X GET "http://52.29.87.138:80/kognihome/userprofiles/status"
```

## Querying static information

### REST

```
curl -X GET "http://52.29.87.138:80/kognihome/userprofiles/query" -d "{ \"uid\": \"katharinabecker\", \"ask\": \"<attribute>\" }"
```
### RSB (synchronous example)
```
query = '{"coll": "info", "uid": "katharinabecker", "ask": "name"}'

with rsb.createRemoteServer('/kognihome/userprofiles/') as server:
    print('server replied to synchronous call: "%s"' % server.query(query))
```


Where `<attribute>` currently covers:

* `name`, `firstname`, `lastname`
* `gender`
* `height`
* `birthdate`, `age` , `hasbirthday`

## Adding documents to the database

### REST

```
curl -X POST "http://52.29.87.138:80/kognihome/userprofiles/write" -d @src/test/document.json
```

### RSB (synchronous example)
```
write = '{ ' \
        '"coll": "foo", ' \
        '"creator": "foo creator" , ' \
        '"doc": {' \
        ' "uid": "foobar" ,' \
        '"name" : "batz", }' \
        '}'

with rsb.createRemoteServer('/kognihome/userprofiles/') as server:
    print('server replied to synchronous call: "%s"' % server.write(write))
```

Where `document.json` has, for example, the following structure:

```
{
  "coll": "test" ,
  "creator": "tester" ,

  "doc": {
           "uid": "alexanderbecker" ,
           "date" : "2015-12-15" ,
           ...
   }
}
```

## Retrieving documents from the database

### REST

```
curl -X GET "http://52.29.87.138:80/kognihome/userprofiles/retrieve" -d "{ \"coll\": \"activitydata\", \"find\": { \"uid\": \"alexanderbecker\", \"date\": \"2015-12-15\" } }"
```

### RSB (synchronous example)
```
request = '{"coll": "foo", "find": { "uid": "foobar", "name": "batz"}}'

with rsb.createRemoteServer('/kognihome/userprofiles/') as server:
    print('server replied to synchronous call: "%s"' % server.request(request))
```
