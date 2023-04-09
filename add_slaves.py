
import jenkins


def info(server):
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

server = jenkins.Jenkins('http://localhost:8080', username='admin', password='admin123')

info(server)

server.create_node('slave1')
node_config = server.get_node_info('slave1')
print(node_config)
server.disable_node('slave1')
#server.enable_node('slave1')

## create node with parameters
#params = {
#    'port': '22',
#    'username': 'juser',
#    'credentialsId': '10f3a3c8-be35-327e-b60b-a3e5edb0e45f',
#    'host': 'my.jenkins.slave1'
#}
#server.create_node(
#    'slave1',
#    nodeDescription='my test slave',
#    remoteFS='/home/juser',
#    labels='precise',
#    exclusive=True,
#    launcher=jenkins.LAUNCHER_SSH,
#    launcher_params=params)
#
# https://python-jenkins.readthedocs.io/en/latest/api.html?highlight=reconfig_nod#jenkins.Jenkins.reconfig_job
# server.reconfig_node(slave['name'], slave_xml)
#
info(server)

#server.delete_node()
