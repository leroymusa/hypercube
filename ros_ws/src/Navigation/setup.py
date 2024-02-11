from setuptools import find_packages, setup

package_name = 'Navigation'

setup(
    name=package_name,
    version='0.0.0',
    packages=find_packages(exclude=['test']),
    data_files=[
        ('share/ament_index/resource_index/packages',
            ['resource/' + package_name]),
        ('share/' + package_name, ['package.xml']),
    ],
    install_requires=['setuptools'],
    zip_safe=True,
    maintainer='leroy7',
    maintainer_email='leroy7@todo.todo',
    description='TODO: Package description',
    license='TODO: License declaration',
    tests_require=['pytest'],
    entry_points={ #TO EDIT (Leroy, Joaquin)
        'console_scripts': [
            "velPID = my_bot.VelPID:main",
            "effortPID = my_bot.EffortPID:main",
            "PID = my_bot.PID:main",
            "udp_conv = my_bot.udp_conv:main"
        ],
    },
)
