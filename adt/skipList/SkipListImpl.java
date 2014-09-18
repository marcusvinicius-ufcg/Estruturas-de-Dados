package adt.skipList;

public class SkipListImpl<V> implements SkipList<V> {

	protected SkipNode<V> root;
	protected int level;
	protected int maxLevel;
	
	// SET THIS VALUE TO TRUE IF YOU ARE IMPLEMENTING MAX_LEVEL = LEVEL
	// SET THIS VALUE TO FALSE IF YOU ARE IMPLEMENTING MAX_LEVEL != LEVEL
	protected boolean useMaxLevelAsLevel = true;
	protected double probability = 0.5;
	protected SkipNode<V> NIL;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SkipListImpl(int maxLevel) {
		this.level = useMaxLevelAsLevel ? maxLevel : 1;
		
		this.maxLevel = maxLevel;
		this.root = new SkipNode(Integer.MIN_VALUE, maxLevel, new Integer(Integer.MIN_VALUE));
		this.NIL  = new SkipNode(Integer.MAX_VALUE, maxLevel, new Integer(Integer.MAX_VALUE));
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		if (level == maxLevel){
			for (int i = 0; i < level; i++){
				root.forward[i] = NIL;
			}
		}else{
			root.forward[0] = NIL;
		}
	}

	/**
	 * Metodo que gera uma altura aleatoria para ser atribuida a um novo no no
	 * metodo insert(int,V)
	 */
	private int randomLevel() {
		Integer randomLevel = 1;
		Double random = Math.random();
		while (random <= probability && randomLevel < maxLevel) {
			randomLevel = randomLevel + 1;
		}
		return randomLevel;
	}

	@Override
	public void insert(int key, V newValue) {
		Integer randomHeight = randomLevel();
		insert(key, newValue, randomHeight);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(int key, V newValue, int height) {
		SkipNode<V>[] update = new SkipNode[maxLevel];
		SkipNode<V> node = root;

		for (int i = (level - 1); i >= 0; i--) {
			while (node.getForward(i).getKey() < key) {
				node = node.getForward(i);
			}
			update[i] = node;
		}
		node = node.getForward(0);

		if (node.getKey() == key) {
			if (node.height == height) {
				node.satteliteData = newValue;
			} else {
				remove(key);
				insert(key, newValue, height);
			}
		} else {
			Integer newLevel = height;

			if (newLevel > level) {
				for (int i = level; i < newLevel; i++) {
					update[i] = root;
				}
				level = newLevel;
			}
			node = new SkipNode<V>(key, newLevel, newValue);
			for (int i = 0; i < newLevel; i++) {
				if (update[i].getForward(i) == null) {
					node.getForward()[i] = NIL;
				} else {
					node.getForward()[i] = update[i].getForward(i);
				}
				update[i].getForward()[i] = node;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(int key) {
		SkipNode<V>[] update = new SkipNode[maxLevel];
		SkipNode<V> x = root;
		for (int i = (level - 1); i >= 0; i--) {
			while (x.forward[i].getKey() < key) {
				x = x.forward[i];
			}
			update[i] = x;
		}
		x = x.forward[0];

		if (x.getKey() == key) {
			for (int i = 0; i < level; i++) {
				if (update[i].getForward(i) != x) {
					break;
				}
				update[i].getForward()[i] = x.getForward(i);
				// FREE(x)
				while (level > 1 && root.forward[level - 1] == NIL) {
					level--;
				}
			}
		}
	}

	@Override
	public int height() {
		Integer height;
		
		for (height = 0; height < level; height++) {
			if (root.forward[height] == NIL) {
				break;
			}
		}
		return height;
	}

	@Override
	public SkipNode<V> search(int key) {
		SkipNode<V> foundNode = root;

		for (int i = (height() - 1); i >= 0; i--) {
			while (foundNode.forward[i].getKey() < key) {
				foundNode = foundNode.forward[i];
			}
		}
		foundNode = foundNode.forward[0];

		if (foundNode.getKey() != key) {
			foundNode = null;
		}
		return foundNode;
	}

	@Override
	public int size() {
		Integer size = 0;
		SkipNode<V> currentNode = root.getForward(0);
		while (currentNode != NIL) {
			size++;
			currentNode = currentNode.getForward(0);
		}
		return size;
	}

	@Override
	public SkipNode<V>[] toArray() {
		@SuppressWarnings("unchecked")
		SkipNode<V>[] array = new SkipNode[size()];
		Integer i = 0;
		
		SkipNode<V> currentNode = root.getForward(0);
		while (currentNode != NIL) {
			array[i++] = currentNode;
			currentNode = currentNode.getForward(0);
		}
		return array;
	}

}
