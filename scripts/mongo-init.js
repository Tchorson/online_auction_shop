print('Creating database');
db = db.getSiblingDB('shop');
print('Creating user');

db.createUser(
{
  user: 'user',
  pwd: 'user',
  roles: ["readWrite"],
});
print('Creating users collection');

db.createCollection('users');

print('Inserting users');

db.users.insertMany([
{
  username: "admin",
  password: "password",
  role: "ROLE_ADMIN"
},
{
  username: "user",
  password: "password",
  role: "ROLE_USER"
}
]);
print('Creating orders');

db.createCollection('orders');

print('Inserting orders');

db.orders.insertMany([
{
  customer: "admin",
  status: "WAITING",
  shippingAddress: "Warsaw, Poland",
  items: [
      {
        name: "Effective Java, Second edition ",
        price: 15.25,
        currency: "Dollar",
        quantity: 1
      }
    ],
},
{
  customer: "user",
  status: "CANCELED",
  shippingAddress: "Cracow, Poland",
  items: [
  {
    name: "Effective Java, Second edition ",
    price: 15.25,
    currency: "Dollar",
    quantity: 1
  }],
},
{
  customer: "user",
  status: "DELIVERED",
  shippingAddress: "Cracow, Poland",
  items: [
  {
    name: "Effective Java, Third edition ",
    price: 35.5,
    currency: "Dollar",
    quantity: 1
  }],
}]);

print('Mongo init process completed');
