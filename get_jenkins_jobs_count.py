
import jenkins

server = jenkins.Jenkins('http://localhost:8080', username='admin', password='admin123')
print(server.jobs_count())
nodes = server.get_nodes()
# [{'name': 'Built-In Node', 'offline': False}]
print("Total Nodes: {}".format(nodes))
for node in nodes:
    node_name = node['name']
    if node_name == 'master' or node_name == 'Built-In Node':
        continue

    slave_node_info = server.get_node_info(node_name)
    print("slave nodes: {}".format(slave_node_info))
